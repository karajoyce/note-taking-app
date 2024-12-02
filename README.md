# 370-01-T05-Team4

## TruNotes
TruNotes is a Java-FX note-taking application for the average student, for the working student, and for the organizing student. TruNotes offers an agenda-like to do list, a notebook system, and a flashcards system.

TruNotes allows you to make Folders that each have their own name and list of Pages/Notes in them that are unique to that folder. Each folder will automatically get their own flashcard deck when they are made. You can choose to manually make flashcards or follow our automated flshcard system explained below in [Usage](#usage).

## Installation
Below is the link to the final product:
https://drive.google.com/drive/folders/1MqCJ2syO-SozMfRiyAIM68sEWmr8FpBS?usp=sharing
Copy and Paste this line in the terminal to start.
java --module-path .\javafx-sdk-23.0.1\lib --add-modules javafx.controls,javafx.fxml,javafx.media -jar .\target\demo-1.0-SNAPSHOT-jar-with-dependencies.ja

## Requirements
Java Runtime 21
Openjdk23/21 SDK

## Usage
This application may have some errors when users who are on MacOS enter into fullscreen mode. We would advice MacOS users to not enter fullscreen mode for best use. 
A to-do item that has a warning symbol in-line with it means it is due within 5 days.
Folder names that are unsupported: "Empty Deck"

For **automated flashcards**, you must follow these simple steps!
- When you are in class taking notes:
1. Have a folder for the class you are in or for where you want the flashcards stored if it is different than the class you are in.
2. In the top right of the tool bar, switch on "Auto Flashcards"
- To make each flashcard:
1. Toggle BOLD text with CTRL+B or the "Bold" button.
2. The text you write in bold will become the front of the flashcard.
3. Before you enter a space after the last bold word, unbold and then enter the space character.
4. Anything you write after the space character will be the back of the flashcard.
5. Use a period "." to end the back of the flashcard.

EX// **HTML** stands for hyper text markup language and is used for Web Development.
This will turn into a flashcard with the front being "HTML" and the back being "stands for hyper text markup language and is used for Web Development"

## Support

## Roadmap
Possible future ideas include and are not limited to:
- hyperlinks in Pages
- improve the UI to a more modern approach
- improve existing features
 - make auto flashcards work when the space is bold
- improve robustness

## Contributing
We are not open to contributors at the moment.

## Authors and acknowledgment
We want to acknowledge the hardwork that all five of our authors have put in. This project was completed to this state on December 1st, 2024 and was started on September 9th, 2024. 
Our five lovely authors are, Kara Leier, Trushank Lakdawala, Sara Shakeel, Nathan Balilis, and Jinny Kim.
