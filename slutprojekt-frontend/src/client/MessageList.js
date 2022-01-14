import React from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import { FixedSizeList } from "react-window";

function renderRow(props) {
    const { index, style } = props;

    return (
        <ListItem style={style} key={index} component="div" disablePadding>
            <ListItemButton>
                <ListItemText primary={`Item ${index + 1}`} />
            </ListItemButton>
        </ListItem>
    );
}

export default function MessageList() {
    return (
        <Box
            sx={{
                width: 1,
                height: 400,
                bgcolor: "background.paper",
            }}
        >
            {/* <FixedSizeList
                height={400}
                itemSize={46}
                itemCount={200}
                overscanCount={5}
            >
                {renderRow}
            </FixedSizeList> */}
            <ListItem alignItems="flex-start"></ListItem>
            <List></List>
        </Box>
    );
}
