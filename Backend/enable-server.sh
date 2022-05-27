#!/bin/bash
cd /home/juanma_caaz_gmail_com/VITON-HD
export GOOGLE_APPLICATION_CREDENTIALS="dressmeapp-348622-firebase-adminsdk-8zkqy-b8fbd66ad7.json"
export FLASK_APP="server_test.py"
flask run --host=0.0.0.0 --port=8000
