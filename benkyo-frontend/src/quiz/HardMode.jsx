import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "../api/axios";

export default function HardMode() {

    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [score, setScore] = useState(0);
    const [questionText, setQuestionText] = useState("");
    const [showNextButton, setShowNextButton] = useState(false);
    const [questions, setQuestions] = useState([]);
    const {codename} = useParams();
    const [answerInput, setAnswerInput] = useState("");
    const [correctAnswer, setCorrectAnswer] = useState("");
    const [showTrueFalse, setShowTrueFalse] = useState(false);
    const [trueOrFalse, setTrueOrFalse] = useState(false);
    const [showCorrectAnswer, setShowCorrectAnswer] = useState(false);

    const startQuiz = () => {
        setCurrentQuestionIndex(0);
        setScore(0);
        setShowNextButton(false);
        showQuestion(0);
    };

    useEffect(() => {
        startQuiz();
    }, [questions]);

    useEffect(() => {
        axios.get('/list/' + codename + '/get-hard-quiz')
        .then((response) => {
            setQuestions(response.data?.questions)
        })
        .catch ((error) => {
            console.log(error);
        });
    }, []);

    const handleNextQuestion = () => {
        setShowNextButton(false);
        setCurrentQuestionIndex((prev) => prev + 1);
        if (trueOrFalse) {
            setScore((prev) => prev + 1);
            console.log("ok");
        }
        
        if (currentQuestionIndex < questions.length - 1) {
            showQuestion(currentQuestionIndex + 1);
        } else {
            showScore();
        }
    };

    const showScore = () => {
        console.log("Your score is " + score);
    }

    const handleChangeAnswer = (e) => {
        setAnswerInput(e.target.value);
    }

    const resetState = () => {
        setAnswerInput("");
        setCorrectAnswer(null);
        setShowCorrectAnswer(false);
        setShowTrueFalse(false);
        setTrueOrFalse(false);
    }

    const showQuestion = (index) => {
        resetState();
        let currentQuestion = questions[index];
        if (!currentQuestion) {
            return;
        }
        let questionNumber = index + 1;
        setQuestionText(`${questionNumber}. ${currentQuestion.question}`);
        console.log(currentQuestion.answer)
        setCorrectAnswer(currentQuestion.answer)
        setShowCorrectAnswer(false);
    };

    const handleSumbitBtn = () => {
        setShowNextButton(true);
        setShowTrueFalse(true);
        setShowCorrectAnswer(true);
    }

    return (
        <div>
            <h1>Hard Mode</h1>
            <div className="quiz">
                <h2> {questionText}</h2>
                <input value = {answerInput} type="text" name="answer"  onChange={handleChangeAnswer}/>
                <button onClick={handleSumbitBtn}>Submit</button>
                {showCorrectAnswer && (
                    <p>Correct Answer: {correctAnswer}</p>
                )}
                {
                    showTrueFalse && (
                        <div>
                            <button type="button" onClick={(e) => setTrueOrFalse(true)}>True</button>
                            <button type="button" onClick={(e) => setTrueOrFalse(false)}>False</button>
                        </div>
                    )
                }
                {showNextButton && (
                    <button onClick={handleNextQuestion}>Next</button>
                )}
            </div>
        </div>
    )
};