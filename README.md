# PitchIt
PitchIt is a Story Pitch Management System which allows authors to create pitches for stories they want published, and for those pitches to be seen by editors. Editors are shown pitches in relevant genres which are given a priority based on how long the pitch has been pending. This allows authors to avoid letting their pitches disappear in email chains, and allows editors to easily communicate with authors and other editors.

## Technologies Used
* Java - SE 8
* Hibernate - Configuration 3.0
* PostgreSQL
* Tomcat Server - Version 9.0.43
* JavaScript
* HTML/CSS

## Features
Currently implemented features:
* Authors can view a snapshot of the pitches they've submitted, and click to view additional information such as the status of the pitch as it travels up the chain of editors
* Authors can create a new pitch with an easy to use popup form
* Editors can view all pitches relevant to their genre committees, and whether they are assistant, standard or senior editors
* When viewing additional info on a pitch, editors may choose to accept, reject, or request additional information about a pitch
* The system will automatically move pitches along the editor chain as they are accepted

## Getting Started
First, clone the repository to the local machine using the following command in the folder where the application will be stored:
    git clone https://github.com/ryanlansdon/PitchIt.git
    
All necessary files are stored in the same project folder, so simply add the project path to a Tomcat Version 9.0.43 Server. Run the server, and view the project in the browser at the following url: http://localhost:8080/Project1/static
