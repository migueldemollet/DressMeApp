import requests

url = 'http://127.0.0.1:8000/upload'
file = {'file': open('input/person.jpg', 'rb')}
resp = requests.post(url=url, files=file, params={'id_prenda': 'PsaE3s5n3qO4yENvlqCF'})
print(resp.files)