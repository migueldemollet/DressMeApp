
const functions = require("firebase-functions");
const admin = require('firebase-admin')
const express = require('express')

const app = express()
//LO AUTENTICAMOS PARA UTILIZAR LOS SERVICIOS
admin.initializeApp({
    credential: admin.credential.cert('./permissions.json'),
    databaseURL: "https://dressmeapp-348622-default-rtdb.europe-west1.firebasedatabase.app"
})

const bd = admin.firestore()


//const uuidv4 = require('uuid/v4');

const { v4: uuidv4 } = require('uuid');




exports.fileAdded = functions
.storage
.object()
.onFinalize(async(object) => {
    
    functions.logger.info(object.bucket);
    functions.logger.info(object.name); //.name es la ruta del archivo
    functions.logger.info(object.contentType);

    
    const uuid = uuidv4(); //generamos antes un id unico
    //Genarmo un ID unico y actualizamos la imagen con el id que acabamos de crear
    //metadata: { firebaseStorageDownloadTokens: uuid }
    const bucket = admin.storage().bucket('dressmeapp-348622.appspot.com');
    const file = bucket.file(String(object.name));
    const metadata = file.metadata;
    //functions.logger.info(metadata.mediaLink);

    const path = object.name;
    const divisionPath = path.split("/");
    const nombreArchivo = divisionPath[divisionPath.length-1];

    await file.setMetadata({
        metadata: {
            firebaseStorageDownloadTokens: uuid,
        }
    })




    const {Storage} = require('@google-cloud/storage');


    const colores = {
        "black": [0,0,0],
        "blue": [0,96,255],
        "brown": [101,67,33],
        "grey": [142,142,142],
        "green": [50,205,50],
        "orange": [235,97,35],
        "pink": [255,20,147],
        "purple": [128,0,128],
        "red": [237,28,36]
    }
    function conseguirColor(R,G,B,colores)
    {
        var etiquetaColor = "blue";
        var mejorResultado = 100000;
        for(const [nombreColor,RGB] of Object.entries(colores))
        {
            var sumatorio = 0;
            sumatorio = Math.abs(R-RGB[0]) + Math.abs(G-RGB[1]) + Math.abs(B-RGB[2]);
            if (sumatorio < mejorResultado)
            {
                mejorResultado = sumatorio;
                etiquetaColor = nombreColor;
            }
        }
        return etiquetaColor;
    }
    // Creates a client
    comienzoLink = "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fshirts%2F";
    const vision = require('@google-cloud/vision');
    const client = new vision.ImageAnnotatorClient();
    const [result] = await client.imageProperties(comienzoLink.concat(nombreArchivo,"?alt=media&token=",uuid));
    

    const colors = result.imagePropertiesAnnotation.dominantColors.colors;
    colors.forEach(color => functions.logger.info(color));


    const colorPredominante  = colors[0]["color"];
    const colorPredominanteR = colorPredominante["red"];
    const colorPredominanteG = colorPredominante["green"];
    const colorPredominanteB = colorPredominante["blue"];



    var coloFinal = conseguirColor(colorPredominanteR,colorPredominanteG,colorPredominanteB,colores);
    functions.logger.info(coloFinal);

    var comienzoLink = "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fshirts%2F";
    var comienzoLink2 = "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fshirts2%2F";



    //Detectamos ahora los objetos que aparecen en la imagen
    const fs = require('fs');
    
    const gcsUri = "gs://dressmeapp-348622.appspot.com/imgs/shirts/".concat(nombreArchivo);
    const [result2] = await client.objectLocalization(gcsUri);
    const objects = result2.localizedObjectAnnotations;
    objects.forEach(object => {
        functions.logger.info(`Name: ${object.name}`);
        functions.logger.info(`Confidence: ${object.score}`);
    });
    var object = objects[0].name;

    const docRef = await bd.collection('garments').doc();

    


    // Create a reference to the file whose metadata we want to retrieve
    
    const file2 = bucket.file("imgs/shirts2/".concat(nombreArchivo));
    const metadata2 = file2.metadata;
    // Get metadata properties
    /*
    getMetadata(forestRef)
    .then((metadata) => {
        nuevoLow = metadata.firebaseStorageDownloadTokens
    })
    */
    //var nuevoLow = metadata2.firebaseStorageDownloadTokens

    await file2.setMetadata({
        metadata: {
            firebaseStorageDownloadTokens: uuid,
        }
    })







    bd.collection('garments').doc(docRef.id).set({brand: "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fbrands%2Fdress_me_app.png?alt=media&token=70dac9d2-9c17-4a78-baf8-02328b4112fa", brand_name: "DressMeApp", 
        color: coloFinal, description:object.concat(" of color ",coloFinal), 
        id: docRef.id, image: comienzoLink.concat(nombreArchivo,"?alt=media&token=",uuid)
        , type: object,
        image_low: "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fshirts2%2F".concat(nombreArchivo,"?alt=media&token=",uuid)})
    
})