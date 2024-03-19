export default function getQuestion(questions) {
    return questions.map((question) => {
        var questionText = question.question;
        var choices = [];
        for ( var key in question.answers) {
            choices.push({
                text : question.answers[key],
                answer : question.correctAnswer === key
            });
        }
        return {
            question : questionText,
            choices : choices
        }
    });
}