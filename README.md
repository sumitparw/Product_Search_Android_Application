# Product_Search_Android_Application
The solution video:https://www.youtube.com/watch?v=_RpseDGV6I8&feature=youtu.be

## Technologies used
NodeJS, Android(Java), Volley library, Glide library, XML, Google play services

## APIs used
eBay API, IP API, Facebook API, Google Customized Search API, eBay Similar API

## Overview
This Android application serves as the Mobile counterpart of the project found at http://productengine.appspot.com/
Product search allows users to search for various Products that are availble on eBay based on current location or any other location entered by the user. Users can know more about the product, similar items, seller message.

## Features
1. The application makes HTTP requests to the Node server which was already created for website using Volley Library.
2. Real time validations are performed using two way data binding in Angular.
3. Users can search products from current location which is caught using ipapi or a custom ZIP.
4. Users can store favorite products(wishlist) which are accessible at all times even after the website is closed. This is done using LocalStorage. THey can append, delete this Wishlist.
5. In case the user want to share the product they can do this using Facebook button which calls FB api and share the product with a custom message, user can also write something before sharing.
6. Product Images are obtained using custom Google Search.
7. Bootstrap is used to make the website responsive and mobile friendly. 8.The Similar Products can be sorted based on several categories in ascending or descending order. 9.Users can directly tweet about the event by just a click of a button. 10.Custom pipes are used in Angular to truncate sentences when long. 11.Various icons are used for product ratings,Shipping info, seller feedback. 12.Clear button resets the application.
8. There are lot of Animations like loading, selection,pagination,Show More which were taken care of.
