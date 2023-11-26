## A-system-for-COVID-19-Data-Processing-and-Analysis
A system for COVID-19 Data Processing and Analysis implemented with Java OOP

## Introduction
In this work, we researched the OpenDataPhilly for more than 300 data set, applications, and APIs related to the city of Philadelphia. This data resource enables government officials, researchers, and the general public to gain a deeper understanding of what is happening in our fair city. The available data sets cover topics such as the environment, real estate, health and human services, transportation, and public safety. We developed a data processing and analyzing system with Java OOP to deal with above data and developed some additional features to enrich the functions of this program. 

## Requirement
Packages required for this program is listed below: 
- [Java](https://github.com/java/): Java Language
- [Python3](https://github.com/python3/): Python Language

## Code files:
- Main.java: Main Function for System StartUps
- Data Management: Data Management Tier
  - Including: CSVLexer.java, CovidCSVReader.java, CovidJSONReader.java, PopulationReader.java, PropertyReader.java, Reader.java
- Logging: Logging Tier
  - Including: Logger.java
- Processor: Processor Function Tier
  - Including: LivableComparable.java, MarketComparable.java, Processor.java, PropertyComparator.java
- UI: User-Interface Tier
  - Including: CommandLineUserInterface.java
- Utility: Utility Tier
  - Including: Population.java, Property.java, Vaccination.java

## Runtime Results:
- In this additional feature we implemented a method which calculates a numerical factor can be used to measure the covid-prevention potential of a certain area in Philadelphia. Its mathematical expression is as follows:
<img src="https://github.com/ZhenyangXuUVA/A-System-for-COVID-19-Data-Processing-and-Analysis/blob/main/Readme/Figure01.png" width="700">

- A best way of validating this feature is to passing several numerical tests: For example, if we chose option 7 which executes additional feature, we continue to pick zip code as “19120” and select date as “2021-08-19”, this will give us calculated result as 16967835. If we look at the component value of above mathematical expression: We have:
<img src="https://github.com/ZhenyangXuUVA/A-System-for-COVID-19-Data-Processing-and-Analysis/blob/main/Readme/Figure02.png" width="370">

- Which yield:
<img src="https://github.com/ZhenyangXuUVA/A-System-for-COVID-19-Data-Processing-and-Analysis/blob/main/Readme/Figure03.png" width="520">

- which matches the result output by the program:
<img src="https://github.com/ZhenyangXuUVA/A-System-for-COVID-19-Data-Processing-and-Analysis/blob/main/Readme/Figure04.png" width="700">

In this project, we have tried to implement the work process taught in class to implement this project. The needs are analyzed first. A N-tier architecture is used to represent different components of program. We have tried to implement the logger tier with singleton design pattern and implement average total livable area with strategy design pattern. Files are read in with different data structures to store information extracted from them. A processor tier is implemented to perform backend calculations along with a user-interface tier to perform frontend user interaction such as take in user’s option and output formatted results. We have learned a lot through this project as it combines nearly all materials taught in class and deepens our understating of class materials through practical programming. Going forward, we will be more careful about design pattern implementation aspect of a future project. In this project we used slack as our primary method of communication and we used GitHub as our code version control method.
