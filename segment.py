import cv2
import imutils
import numpy as np
from matplotlib import pyplot as plt

# Run GrabCut
img = cv2.imread('test.png')
img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)
mask = np.zeros(img.shape[:2],np.uint8)
bgdModel = np.zeros((1,65),np.float64)
fgdModel = np.zeros((1,65),np.float64)
rect = (50,0,280,500)
cv2.grabCut(img,mask,rect,bgdModel,fgdModel,5,cv2.GC_INIT_WITH_RECT)
mask2 = np.where((mask==2)|(mask==0),0,1).astype('uint8')
img = img*mask2[:,:,np.newaxis]
plt.imshow(img),plt.show()
