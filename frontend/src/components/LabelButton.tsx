import * as React from 'react';
import Button from '@mui/material/Button';

interface Props {
  name: string;
}

export default function BasicButtons(props: Props) {
  return (
    <Button variant="contained" disableElevation style={{ height: '100.2%' }} onClick={() => { alert('clicked'); }}>
      {props.name}
    </Button>
  );
}