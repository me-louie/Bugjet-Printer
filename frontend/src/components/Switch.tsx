import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import Switch from '@mui/material/Switch';

interface Slice {
  name: string;
  program: string;
  show: boolean;
}

interface Props {
  slices: Slice[];
  toggleShowSlice: (name: string) => void;
}

export default function SwitchList(props: Props) {
  // const [checked, setChecked] = React.useState(["double a"]);

  // const handleToggle = (value: string) => () => {
  //   const currentIndex = checked.indexOf(value);
  //   const newChecked = [...checked];

  //   if (currentIndex === -1) {
  //     newChecked.push(value);
  //   } else {
  //     newChecked.splice(currentIndex, 1);
  //   }

  //   setChecked(newChecked);
  // };

  // // change names dependingh on the json object. 
  // const obj = {
  //   name: 'double a'
  // }

  return (
    <List
      sx={{ width: "100%", maxWidth: 200, bgcolor: "background.paper" }}
      subheader={<ListSubheader>Varaible Names:</ListSubheader>}
    >
      {props.slices.map((slice => (
        <ListItem key={slice.name}>
          <ListItemText id={`switch-list-label-${slice.name}`} primary={slice.name} />
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
      {/* <ListItem>
          <ListItemText id="switch-list-label-b" primary="double b" />
          <Switch
            edge="end"
            onChange={handleToggle("double b")}
            checked={checked.indexOf("double b") !== -1}
            inputProps={{
              "aria-labelledby": "switch-list-label-variable-b"
            }}
          />
        </ListItem> */}
    </List>
  );
}
