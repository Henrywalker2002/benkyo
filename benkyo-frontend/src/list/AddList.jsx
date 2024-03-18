import * as React from 'react';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { Button } from '@mui/material';
import axios from '../api/axios';
import Alert from '@mui/material/Alert';

export default function AddList(props) {

    const [showAlert, setShowAlert] = React.useState(false);
    const [showError, setShowError] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState('');

    const handleSummit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const formJson = Object.fromEntries(formData.entries());
        console.log(formJson);
        var data = {
            "friendlyName": formJson.friendly_name,
            "codeName": formJson.code_name,
            "description": formJson.description
        }
        axios.post('/list', data).then((response) => {
            console.log(response);
            setShowAlert(true);
            setTimeout(() => {
                props.handleClose();
                setShowAlert(false);
            }, 2000);
        })
        .catch((error) => {
            setErrorMessage(error.response.data);
            setShowError(true);
        });
    }

    return (
        <Dialog open = {props.state} 
            onClose={props.handleClose} 
            PaperProps={{
                component: 'form',
                onSubmit: (event) => {
                    handleSummit(event);
                },
        }}>

            <DialogTitle>Add List</DialogTitle>
            <DialogContent>
                {showAlert && <Alert severity="info" >Add a new list</Alert> }
                {showError && <Alert severity="error" >{errorMessage}</Alert> }
                <TextField
                    autoFocus
                    required
                    margin="dense"
                    id="friendly_name"
                    name = "friendly_name"
                    label="Friendly name"
                    type="text"
                    fullWidth
                    variant="standard"
                />

                <TextField
                    autoFocus
                    required
                    margin="dense"
                    id="code_name"
                    name = "code_name"
                    label="Code name"
                    type="text"
                    fullWidth
                    variant="standard"
                />

                <TextField
                    autoFocus
                    required
                    margin="dense"
                    id="description"   
                    name = "description"
                    label="Description"
                    type="text"
                    fullWidth
                    variant="standard"
                />

                <DialogActions>
                    <Button onClick={props.handleClose}>Cancel</Button>
                    <Button type="submit">Add</Button>
                </DialogActions>
            </DialogContent>

        </Dialog>
    )
}