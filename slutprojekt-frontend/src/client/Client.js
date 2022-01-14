import React, { useState, useEffect } from "react";
import Button from "@mui/material/Button";

import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import Messages from "../components/Messages";

import SockJS from "sockjs-client";
const Item = styled(Paper)(({ theme }) => ({
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: "center",
    color: theme.palette.text.secondary,
}));

const Client = () => {
    const [messages, setMessage] = useState([]);
    const [socket, setSocket] = useState();

    useEffect(() => {
        var socket = new SockJS("http://localhost:8080/greeting");

        socket.onopen = () => {
            console.log("Connection open!");
            socket.send("sensor");
        };

        socket.onmessage = (e) => {
            setMessage((prevMessages) => {
                console.log(prevMessages);
                return [
                    ...prevMessages,
                    {
                        timestamp: new Date(),
                        message: e.data,
                    },
                ];
            });
            console.log("Message received: ", e.data);
        };

        socket.onclose = () => {
            console.log("Closed");
        };

        setSocket(socket);
    }, []);

    const sendMessage = () => {
        socket.send("sensor");
    };

    return (
        <div>
            <Grid container xs={12} spacing={2} item={true}>
                <Grid item xs={8}>
                    <Button
                        onClick={sendMessage}
                        style={{ minWidth: "100%" }}
                        variant="contained"
                    >
                        Update clients
                    </Button>
                </Grid>
                <Grid item xs={8}></Grid>
                <Grid item xs={12}>
                    <Messages messages={messages}></Messages>
                </Grid>
            </Grid>
        </div>
    );
};

export default Client;
