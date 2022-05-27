from flask import Flask, request, json, jsonify
from google.cloud import firestore, storage
import base64
import os
import time

app = Flask(__name__)

@app.route('/upload', methods=['POST'])
def process_json():
   
    content_type = request.headers.get('Content-Type')
    if (content_type == 'application/json'):
        print("JSON")
        os.system('cd /home/juanma_caaz_gmail_com/VITON-HD')
        db = firestore.Client()
        storage_client = storage.Client()
        bucket = storage_client.bucket('dressmeapp-348622.appspot.com')

        json = request.json
        url_prenda_img = db.collection(u'garments').document(json['id_prenda']).get().to_dict()['image']
        os.system(f'wget -O input/clothe.jpg {url_prenda_img}')
        imgdata = base64.b64decode(json['image'])
        try:
            os.remove('input/person.jpg')
        except: pass
        filename = 'input/person.jpg'

        with open(filename, 'wb') as f:
            f.write(imgdata);
        time.sleep(3)
        os.system('./preprocessing.sh')
        
        img_data = bucket.blob(f'{str(round(time.time() * 1000))}.jpg')
        img_data.upload_from_filename('results/datasets/test_pairs.txt/person.jpg_clothe.jpg')
        img_data.make_public()
        url_img = img_data.public_url
        print(url_img)
        return url_img

    else:
        print("Not JSON")
        return 'Content-Type not supported!'
