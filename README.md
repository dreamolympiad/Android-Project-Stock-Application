# Android-Project-Stock-Application

Stock Search Android App with Facebook Post - Android Studio Project

An Android Mobile Application with the following functionality:

An Auto-complete edit box is provided to enter the stock name or symbol. The user can then select 
a stock from the suggestions.

Once the user has provided data and selected a result from the autocomplete list he would 
click on ‘Get Quote’, when validation must be done to check that the entered data is valid.

Once the validation is successful, we would get the stock details using our PHP script hosted 
on Amazon Web Services/Google App Engine, which would return the result in JSON format.

Display the stock details in a ListView component in the ‘Current’ tab

PHP script would be responsible for rendering the HighCharts in the ‘Historical’ 
tab and also rending the news articles in the ‘News’ tab

