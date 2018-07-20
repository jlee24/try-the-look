#!/usr/bin/env python

import numpy as np
import cv2

from scipy import misc
from flask import Flask, request, jsonify, send_file
from io import BytesIO
from PIL import Image

app = Flask(__name__)

@app.route('/')
def hello():
  return "Hello, world!"

@app.route('/api/v1/try_look', methods=['POST'])
def try_the_look():
  if 'file' not in request.files:
    return jsonify({
      'error': 'Could not find user image in POST files'
    }), 400

  file = request.files['file']
  np_img = np.asarray(Image.open(file))
  # img = np.fromstring(file.read(), dtype=np.uint8)
  print(type(np_img), np_img.shape)
  # print(np_img)

  to_save = Image.fromarray(np_img)
  to_save.save('dummy.jpg')
  return send_file('dummy.jpg', mimetype='image/gif')

if __name__ == '__main__':
  app.run(port=8080, debug=True)
