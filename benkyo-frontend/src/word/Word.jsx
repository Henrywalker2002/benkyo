export default function  Word(props) {
    let word = props.word;

    return (
        <div className= {word.slug}>
            <span>{word.slug}</span>
            <span>{word.kanji}</span>
            <span>{word.hiragana}</span>
            <span>{word.meaning}</span>
        </div>
    )
}