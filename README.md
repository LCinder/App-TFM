## Development of a system for detection of fake news in relevant news on the Internet
### Master's Thesis

---
The information that is currently generated every minute is uncontrollable and practically impossible to verify. In each of the 575,000 tweets and thousands of news items
published in the press every minute there is too much data, which together with the ease
of manipulation and the possibility of sharing this information, the so-called fake news
coexist with us every day, generating distrust in any news obtained on the Internet.
The aim of this project is to create an Android application that allows to instantly obtain
news published on Twitter related to a search term, which are contrasted by a system
created for this purpose indicating the level of veracity of these news.

Specifically, two supervised machine learning models have been created: LSTM-type recurrent neural networks and BERT-type transformers to solve a classification problem
based on Natural Language Processing (NLP) with the aim of observing the differences,
advantages and disadvantages between the two models and selecting the one that best
suits the proposed problem.

In addition, an API has been created to obtain all the necessary information from Twitter,
such as URL’s to news items, the body of the tweet, and even to obtain all the information related to a news item such as the date, source and author. This information, with
its corresponding classification as real or fake, is sent to the application to be shown to
the user. In addition, graphs are available that indicate the relevance of the news item
throughout the week along with the proportion of real/fake news made by the classifier
for the items obtained.

In conclusion, the creation of an application available to any user and the possibility of
accessing and reading any news item that has been classified according to its veracity
guarantees the accessibility of information in any corner of the world.

---

### API

https://github.com/LCinder/TFM