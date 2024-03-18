import AddList from "./AddList";
import Button from '@mui/material/Button';
import React, { useEffect } from 'react';
import axios from "../api/axios";
import { useNavigate } from "react-router-dom";

export default function List() {

    const [open, setOpen] = React.useState(false);
    const [lists, setLists] = React.useState([]); 
    const nagative = useNavigate();
    const handleClickOpen = () => {
        setOpen(true);
    }

    const handleClose = () => {
        setOpen(false);
    }

    useEffect(() => {
        axios.get('/list').then((response) => {
            setLists(response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    }, []);

    return (
        <React.Fragment>
            {lists.map((list) => {
                return <div id= {list.codeName} onClick={ () => {nagative('/list/' + list.codeName)}}>
                    <p>{list.codeName} </p>
                    
                </div>
            })}

            <Button onClick={handleClickOpen}>Add List</Button>
            <AddList state={open} handleClose = {handleClose}/>

        </React.Fragment>
    )
}