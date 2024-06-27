//const { westminsterLargerCatechism } = require("../data/formatting/input");
const {
  formattedArray,
} = require("../data/standards/westminster/catechisms/shorter/shorterCatechismFixedNoScripture.js");

const {
  convertAnswerStringToArray,
  convertStringsToArraysInProofTexts,
  convertProofTextVersesWithNoBook,
  convertProofTextStringToObject,
  convertBibleBookToFullName,
  createOutputOfFormattedArray,
  findAnswerAndProofTextLengthMismatch,
  findUniqueNamesForBibleBooks,
} = require("../utilities/formatting");


//  Map through arrays in proofTexts array to find strings with no letters and add the book name from the previous string to the string
//let conversionRoundThree = convertProofTextVersesWithNoBook(formattedArray);

// NOT USED Convert the strings in the arrays of the proofTexts array to be objects with fields: book, verse, and text
//let conversionRoundFour = convertProofTextStringToObject(conversionRoundThree);
// let roundFive = findUniqueNamesForBibleBooks(conversionRoundFour);
// AFTER MANUAL fix of split book refs Convert the bible book abbreviations in the book field to the full book name

let conversionRoundFive = convertBibleBookToFullName(formattedArray);

// 6. Output Reformatted Array to new File
const newFilePath = "data/formatting/output/shorterWithText.js";
createOutputOfFormattedArray(conversionRoundThree, newFilePath);
//findAnswerAndProofTextLengthMismatch(conversionRoundFive);
