#!/usr/bin/env python

import numpy as np
import cv2

from scipy import misc
from flask import Flask, request, jsonify
from io import BytesIO

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
  img = np.fromstring(file.read(), dtype=np.uint8)
  print(type(img), img.size)

  return jsonify("Found user image")

if __name__ == '__main__':
  app.run(port=8080, debug=True)
