start_time=$(date +%s.%3N)
rm -rf tmp

mkdir -p tmp/person/final
mkdir -p tmp/clothe
mkdir -p tmp/person/body/input
mkdir -p tmp/person/body/output

python3 preprocessing.py

./openpose.bin --image_dir tmp/person/body/input/ --model_pose COCO --disable_blending -write_images tmp/person/body/output/ --write_json tmp/person/body/output/

cp tmp/person/final/person.jpg tmp/person/body/input/person.jpg

./openpose.bin --image_dir tmp/person/body/input/ --model_pose BODY_25 --disable_blending -write_images tmp/person/body/output/ --write_json tmp/person/body/output/ --hand -display 0
end_time=$(date +%s.%3N)

elapsed=$(echo "scale=3; $end_time - $start_time" | bc)

/bin/cp -f tmp/clothe/clothe.jpg datasets/test/cloth/clothe.jpg
/bin/cp -f tmp/clothe/clothe.png datasets/test/cloth-mask/clothe.jpg

/bin/cp -f tmp/person/final/person.jpg datasets/test/image/person.jpg
/bin/cp -f tmp/person/final/person.png datasets/test/image-parse/person.png

/bin/cp -f tmp/person/body/output/person_rendered.png datasets/test/openpose-img/person_rendered.png
/bin/cp -f tmp/person/body/output/person_keypoints.json datasets/test/openpose-json/person_keypoints.json

python3 test.py --name datasets/test_pairs.txt

echo "Time taken: $elapsed seconds"
