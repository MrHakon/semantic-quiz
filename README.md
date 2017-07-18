# semantic-quiz



NOTE:
In order to run this program, you need to install/import Apache Jena (https://jena.apache.org/)

Known bugs:
Country-based SPARQL-queries return multiple values for each predicate per resource, due to changes
made by DbPedia in predicate-naming.

Recent improvements:
- Improved scorings system:
	Rather than right or wrong, it's now based on points. 10 points for each correct answer, and 5 points for each correct answer where hints were requested.
- It's now possible to add functionality to skip a question if there isn't enough data, without it counting as an attempt by the user.

Update plans:
- Implement a global hint-system
- Implement more categories (requires the first category to work more fluently)
- Try to create some sort of GUI
