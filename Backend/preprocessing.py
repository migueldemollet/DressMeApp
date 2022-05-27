from time import sleep
import cv2
import os
import sys
from pathlib import Path

import numpy as np
import matplotlib.pyplot as plt

sys.path.insert(1, 'U-2-Net')

import u2net_load
import u2net_run

EXTRA = [128, 5]

EXTRA2 = [37, 12]

LEFT_HAND = [147, 14]
RIGHT_HAND = [108, 15]

CAMISETA = [14, 5]

FALDA = [52, 9]
PANTALON = [89, 12]

LEFT_PIERNA = [72, 16]
RIGHT_PERNA = [33, 17]

PELO = [75, 1]
CUELLO = [132, 4]

NOSE1 = [113, 4]

cambios = [
    EXTRA,
    EXTRA2,
    CAMISETA,
    LEFT_HAND,
    RIGHT_HAND,
    PELO,
    FALDA,
    PANTALON,
    LEFT_PIERNA,
    RIGHT_PERNA,
    CUELLO,
    NOSE1
]

def chande_color(img, color):
    '''
        color[0] actual color
        color[1] new color
    '''
    img[img == color[0]] = color[1]
    return img

# Image size 768 x 1024

OUTPUT_DIRECTORY = 'datasets/test/'

clothe = 'input/clothe.jpg'
person = 'input/person.jpg'

person = cv2.imread(person)
clothe = cv2.imread(clothe)

clothe = cv2.resize(clothe, (768, 1024))
person = cv2.resize(person, (768, 1024))

u2net = u2net_load.model(model_name = 'u2netp')

# Realizamos la inferencia de la ropa

cv2.imwrite('tmp/clothe/clothe.jpg', clothe)
u2net_run.infer(u2net, 'tmp/clothe', 'tmp/clothe')

to_gray = cv2.imread('tmp/clothe/clothe.png')
to_gray = cv2.cvtColor(to_gray, cv2.COLOR_BGR2GRAY)
cv2.imwrite('tmp/clothe/clothe.png', to_gray)

# Realizamos la inferencia de la persona

cv2.imwrite('tmp/person/person.jpg', person)
u2net_run.infer(u2net, 'tmp/person', 'tmp/person')

# Realizamos la mascara de la persona

person_f = cv2.imread('tmp/person/person.jpg')
person_mask = cv2.imread('tmp/person/person.png')
person_mask = cv2.cvtColor(person_mask, cv2.COLOR_BGR2GRAY)
person_mask[person_mask > 127] = 255
person_mask[person_mask <= 127] = 0
person_f[person_mask < 127] = 255

cv2.imwrite('tmp/person/final/person.jpg', person_f)
os.system("python3 Self-Correction-Human-Parsing/simple_extractor.py --dataset atr --model-restore Self-Correction-Human-Parsing/atr.pth --input-dir tmp/person/final --output-dir tmp/person/final")

final = cv2.imread('tmp/person/final/person.png', 0)

for i in range(len(cambios)):
    final = chande_color(final, cambios[i])
    
cv2.imwrite('tmp/person/final/person.png', final.astype(np.uint8))
