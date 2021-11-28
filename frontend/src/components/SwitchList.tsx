import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import Switch from '@mui/material/Switch';
import { Output } from '../mocks/output';

interface Props {
  slices: (Output & { show: boolean })[];
  toggleShowSlice: (name: string) => void;
}

export default function SwitchList(props: Props) {
  return (
    <List
      sx={{ width: "100%", maxHeight: 250, bgcolor: "background.paper", margin: "auto",
            marginBottom: 3, borderRadius: 2, border: '1px solid black', boxSizing: 'border-box', padding: 0, overflow: 'auto' }}
      subheader={<ListSubheader style={{ borderTopRightRadius: 7, borderTopLeftRadius: 7, backgroundColor: '#1976d2', color: 'white', position: 'relative' }}>Variable Names:</ListSubheader>}
    >
      {props.slices.map((slice => (
        <ListItem key={slice.name} style={{ borderTop: '1px solid black' }}>
          <ListItemText id={`switch-list-label-${slice.name}`} primary={`${slice.type} ${slice.name}`} />
          <Switch
            edge="end"
            onChange={() => props.toggleShowSlice(slice.name)}
            checked={slice.show}
            inputProps={{
              "aria-labelledby": "switch-list-label-variable-a"
            }}
          />
        </ListItem>
      )))}
    </List>
  );
}
