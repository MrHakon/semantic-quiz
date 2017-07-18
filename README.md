# semantic-quiz



NOTE:
In order to run this program, you need to install/import Apache Jena (https://jena.apache.org/)

Known bugs:
Country-based SPARQL-queries return multiple values for each predicate per resource, due to changes
made by DbPedia.

Update plans:
- Improve the scoring system:
	Right now, the quiz-method returns a boolean value, used to increase the score counter. Sometimes
	there isn't enough data to construct a question, and this should result in a new question, rather than
	count as an attempt.
- Implement a global hint-system
- Implement more categories (requires the first category to work more fluently)
- Try to create some sort of GUI