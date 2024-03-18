import React from "react";
import Options from "./Options";

export default function Question(props) {
    const {question, selectedOption, onOptionChange, onSubmit} = this.props;

    return (
        <div>
            <h5 className="mt-2">{question.question}</h5>
            <form onSubmit={onSubmit} className="mt-2 mb-2">
                <Options
                    options={question.answers}
                    selectedOption={selectedOption}
                    onOptionChange={onOptionChange}
                />
                <button type="submit" className="btn btn-primary mt-2">Submit</button>
            </form>
        </div>
    )
}