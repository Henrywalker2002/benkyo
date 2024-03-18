import React, {useState} from "react";
import axios from "../api/axios";
import CreatableSelect from "react-select/creatable";


export default function AppendWord() {
    const [options, setOptions] = React.useState([]);
    const [value, setValue] = React.useState(null);
    const [isLoading, setIsLoading] = React.useState(false);

    React.useEffect(() => {
        axios.get('/word').then((response) => {
            var words = response.data;
            var option_lst = words.map((word) => {
                var label = word.hiragana + " (" + word.meaning + ")" + word.kanji;
                var value = word.slug;
                return {"label": label, "value": value, "is_existing": true}
            })
            setOptions(option_lst);
        }).catch((error) => {
            console.log(error);
        });
    }, [])

    const handleCreate = (inputValue) => {
        setIsLoading(true);
        var data = {
            "hiragana": inputValue,
            "type" : "hiragana"
        }
        axios.post('/word', data).then((response) => {
            setIsLoading(false);
            var new_word = response.data;
            var new_option = {"label": new_word.hiragana + " (" + new_word.meaning + ")", "value": new_word.slug}
            setOptions([...options, new_option]);
            setValue(new_option);
        }).catch((error) => {
            console.log(error);
            setIsLoading(false);
        });
    };

    return (
        <CreatableSelect 
            isClearable     
            options={options}
            isDisabled = {isLoading}
            isLoading = {isLoading}
            name="word"
            onChange={(value) => setValue(value)}
            onCreateOption={handleCreate}
            value = {value}
        />  
    )
    
}