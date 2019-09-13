CommandLineApplication.class: FarmScheduler.class CropCSVReader.class Calendar.class SchedulerConstants.class CommandLineApplication.java
	javac CommandLineApplication.java

FarmScheduler.class: Crop.class CropField.class Calendar.class FarmScheduler.java
	javac FarmScheduler.java

CropField.class: Crop.class CropField.java
	javac CropField.java

CropCSVReader.class: Crop.class CropCSVReader.java
	javac CropCSVReader.java

Catalog.class: Crop.class Catalog.java
	javac Catalog.java

Calendar.class: Crop.class Calendar.java
	javac Calendar.java

Crop.class: SchedulerConstants.class Crop.java
	javac Crop.java

SchedulerConstants.class:
	javac SchedulerConstants.java

clean:
	rm *.class
