import * as React from 'react'
import { Form, useParams } from 'react-router-dom'
import axios from '../api/axios'
import Word from '../word/Word'
import AppendWord from './AppendWord'
import { Button, FormControl } from '@mui/material'

export default function ListDetail(props) {
    const {codeName} = useParams()
    const [list, setList] = React.useState()
    const [words, setWords] = React.useState([])

    React.useEffect(() => {
        axios.get('/list/' + codeName).then((response) => {
            setList(response.data);
            setWords(response.data.words);
        })
        .catch((error) => {
            console.log(error);
        });
    }, [])

    const handleSummit = (event) => {
        event.preventDefault()
        const formData = new FormData(event.currentTarget);
        const formJson = Object.fromEntries(formData.entries());
        var data = {
            "slug" : formJson.word
        }
        axios.post('/list/' + codeName + '/word', data).then((response) => {
            setWords((prev) => [...prev, response.data]);
        })
        .catch((error) => {
            console.log(error);
        });
    }

    return (
        <React.Fragment>
            <h1>{list?.friendlyName}</h1>
            <p>{list?.description}</p>
            {words.map((word) => {
                return <Word word={word}/>
            })}
            <form onSubmit={handleSummit}>
                <FormControl onSubmit = {handleSummit} fullWidth>
                    <AppendWord />
                    <Button type="submit">Add Word</Button>
                </FormControl>
            </form>
        </React.Fragment>

    )
}