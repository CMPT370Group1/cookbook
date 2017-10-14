# cookbook

With the significantly increasing emphasis people put on their daily diet, it has been a big issue 
for taking care of our stomach. Cooking Recipe System is developed to let people create their personal
recipe, save any of your favourite recipes that you just find it online and be exposed to foreign food culture.
Whatever you like, whatever you want, you can find from our application. Moreover, CookBook will save you times 
by organizing all of recipes and keep those in one place, so whenever you need a cooking instruction, CookBook is
here to help you. So you can get the CookBook application on your device, if you are talented for cooking, if
you want a stage, do not hesitate and show us what you have! 

CookBook will help users customize and manage their recipes collection. In a simple interface, the users can have access to storage,
and perform tasks such as browsing, searching, customizing and managing their choices.
These functions will assist the users to find their favourite recipes instantly by allowing them to 
search by different attributes. Once they get their recipes, they can add it into their profile for the next
time they want to cook that delicious meal again, it is there for them. Or they can customize their own recipes
and share it with other people in just a few steps. Hence, our application is not only for people who is already 
an expert chef, but also to those who just want to improve their daily meals or in the way of discovering their passion
for cooking. 


The software will cover the following functions:

CookBook has 3 main pages on the first page which are “My recipes”, “Discover”, and “Tool”:

“My recipes”: in this page, there are four tabs: "ALL" recipes, "ADD" new recipe, "SEARCH" and "LINKS".
In "ALL", all categories of recipes will be displayed such as “BreakFast”,
“Snacks”, “Soups”, etc. The users can choose a specific category, for example “Breakfast”,
then they will see a collection of recipes and they can also “ADD” new recipes into their
personal collection. The user can use “SEARCH” function to look up a recipe, it can
be completed by a basic search or an advanced search which will search recipes by different attributes
(such as Title, Category, Duration, etc.). “LINKS” will display all saved web-link for the user.

“Discover”: in this page, there are three tabs: "ALL" recipes, "SEARCH" and "WEB-SEARCH". In "ALL",
the user can access other users' recipes, and in "SEARCH", the user can search other users' collections.
The user can also perform web-search in "WEB_SEARCH" tab and save either the full recipe or
the web-link into his/her collection for future use.

“Tool”: in this page, there are the following tabs: "ACCOUNT", "MASS", "VOLUME", "TEMPERATURE" and
"MASS-VOLUME". The first one lets the user access his/ her account and modify it. If the user is not
signed in, the sign/register form will be displayed there. The next three tabs are unit converters
that let the user convert different mass, volume and temperature units. The last tab is
“Mass-to-volume converter” for the most common ingredients. The user can add, modify and delete
conversion rules for ingredients.

THINGS WE HAVE COMPLETED:

BASIC UNIT CONVERSION TOOLS such as Mass,Volume and Temperature. 

Users have the ability to convert to different masses 
Users have a huge range of volume metric units to convert from and to such as tablespoon to pint and the like.
Users can change the temperature to either Fahrenheit or Celsius.
Users have the ability to change from mass to volume metrics

USER INTERFACE

It's semi-functional, and some layouts have not been created yet.

MainActivity is a main screen with the application title and login/register button that calls 
another activity, AccountActivity.
AccountActivity has user registration and signin.
RecipesActivity is essentially user’s collection of recipes, in which the user can view all his/her recipes
arranged by category, search for, modify or delete recipes.
DiscoverActivity is where user can view, search and save recipes created by other users. There will be also a web-search
option with possibility to save a web-page link or a full recipe from that page.
ToolsActivity gives the user possibility to view and modify his/her account and use unit converters. If the user has not 
registered or logged in, there will be signin/register page instead of account information.

ACCOUNT REGISTRATION AND SIGN IN

Users can register and log in into their accounts and the system makes all appropriate checks for
these functions and displays an error message on failure.
