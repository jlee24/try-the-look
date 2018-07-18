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
# plt.imshow(img),plt.colorbar(),plt.show()
plt.imshow(img),plt.show()


# Skin Detector
# lower = np.array([0, 48, 80], dtype = "uint8")
# lower = np.array([0, 30, 50], dtype = "uint8")
# upper = np.array([20, 255, 255], dtype = "uint8")
#
# frame = cv2.imread('test.png')
# frame = imutils.resize(frame, width = 400)
# converted = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
# skinMask = cv2.inRange(converted, lower, upper)
#
# # apply a series of erosions and dilations to the mask
# # using an elliptical kernel
# kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (11, 11))
# skinMask = cv2.erode(skinMask, kernel, iterations = 2)
# skinMask = cv2.dilate(skinMask, kernel, iterations = 2)
#
# # blur the mask to help remove noise, then apply the
# # mask to the frame
# skinMask = cv2.GaussianBlur(skinMask, (3, 3), 0)
# skin = cv2.bitwise_and(frame, frame, mask = skinMask)
#
# # show the skin in the image along with the mask
# cv2.imshow("images", np.hstack([frame, skin]))
# cv2.waitKey(0)
# plt.imshow(np.hstack([frame, skin])),plt.show()
