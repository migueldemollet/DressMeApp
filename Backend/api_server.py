import subprocess
from fastapi import FastAPI, File, UploadFile, Response
import fastapi

from google.cloud import firestore

app = FastAPI()

@app.post("/upload")
async def process(file: UploadFile = File(...), id_prenda: str = None):
    db = firestore.Client()
    url = db.collection(u'garments').document(id_prenda).get().to_dict()['image']
    subprocess.run(f'wget -O input/clothe.jpg {url}', shell=True, check=True, executable='/bin/bash')

    try:
        contents = await file.read()
        with open('input/person.jpg', 'wb') as f:
            f.write(contents)
    except Exception:
        return {"message": "There was an error uploading the file"}
    finally:
        await file.close()

    subprocess.run('./preprocessing.sh', shell=True, check=True, executable='/bin/bash')

    return fastapi.responses.FileResponse('results/datasets/test_pairs.txt/person.jpg_clothe.jpg', media_type='image/jpeg')