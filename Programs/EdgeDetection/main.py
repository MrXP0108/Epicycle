import numpy as np
import cv2

img = cv2.imread('NTU.png')
img = cv2.resize(img, (0, 0), fx=0.5, fy=0.5)
cv2.imshow('Original Image', img)
img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
img = cv2.Canny(img, 70, 210)
cv2.imshow('After Edge Detection', img)
# cv2.waitKey(0)
#
# img_blurred = cv2.GaussianBlur(img, (5, 5), 5)
# cv2.imshow('Blurred Image', img_blurred)
#
# img_stdized = img / 255
# noise = np.random.normal(0, 0.2, img_stdized.shape)
# img_noised = img_stdized + noise
# cv2.imshow('Noise Added', img_noised)
#
# img_noised_and_blurred = cv2.GaussianBlur(img_noised, (5, 5), 5)
# cv2.imshow('Noised and Blurred Image', img_noised_and_blurred)
import csv

csvPath = 'C:/Users/addyx/IdeaProjects/DrawingWithEpicycles/src/Data/edge coordinates.csv'
with open(csvPath, 'w', newline='') as file:
    writer = csv.writer(file)
    for row in range(img.shape[0]):
        for col in range(img.shape[1]):
            if img[row][col] > 250:
                writer.writerow([col, row])

