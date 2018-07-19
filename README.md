# try-the-look

# How to set up the repo
1. pip install virtualenv
2. Clone this repo into a directory of your choice.
3. In that same directory, create a virtual environment with `virtualenv .env`. Then activate it with `source .env/bin/activate`.
4. Now that we're inside our virtual environment, install the requirements with `pip install -r requirements.txt`. (Note: You will probably have to install other libraries to get the code running, so just address the errors as they arise.)

# How to actually run things
There are two main parts of our pipeline, 1. getting a clean mask to extract a body from an image and 2. warping the model's body into the user's. We are following this paper: https://link.springer.com/content/pdf/10.1007%2Fs41095-017-0084-6.pdf.

Right now there is code mainly for the first step.

- GrabCut: https://docs.opencv.org/3.4/d8/d83/tutorial_py_grabcut.html. 
- Saliency Map: https://github.com/Joker316701882/Salient-Object-Detection
- Skin Detector: TBD

To run GrabCut: 
`python segment.py`

To get saliency map:
- Setup: Create a folder called `salience_model` in this repo (make sure not to push this). Download the pretrained model from https://drive.google.com/open?id=0B6l9O8aWij8fUGtVNldUTXA4eHc and move it into the folder.
- Run the code on an image or directory of images, with this format `python inference.py --rgb=[your image or your folder]`, e.g. `python inference.py --rgb=test.png`

