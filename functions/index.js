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
    functions.logger.info(metadata.mediaLink);

    const path = object.name;
    const divisionPath = path.split("/");
    const nombreArchivo = divisionPath[divisionPath.length-1];

    /*
    await admin.storage().bucket('dressmeapp-348622.appspot.com').upload(String(object.name), {
        //destination: object.name, 
        metadata: {
            metadata: {
                firebaseStorageDownloadTokens: uuid,
            }
        },
    });
    */
    await file.setMetadata({
        metadata: {
            firebaseStorageDownloadTokens: uuid,
        }
    })
    var comienzoLink = "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fshirts%2F";
    /*
    await bd.collection('garments')
    .doc()
    .create({brand: "", brand_name: "", 
        color: "", description: ("of color"), 
        id: "", image: comienzoLink.concat(nombreArchivo,"?alt=media&token=",uuid)
        , type: ""}).then(function(docRef) {
            functions.logger.info(docRef.id);
        })
    //return res.status(204).json()
    */

    const docRef = await bd.collection('garments').doc();

    bd.collection('garments').doc(docRef.id).set({brand: "https://firebasestorage.googleapis.com/v0/b/dressmeapp-348622.appspot.com/o/imgs%2Fbrands%2Fdress_me_app.png?alt=media&token=70dac9d2-9c17-4a78-baf8-02328b4112fa", brand_name: "DressMeApp", 
        color: "red", description: ("pants of color red"), 
        id: docRef.id, image: comienzoLink.concat(nombreArchivo,"?alt=media&token=",uuid)
        , type: "pants"})
})