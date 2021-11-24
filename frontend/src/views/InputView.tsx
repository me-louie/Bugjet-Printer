import { Button } from '@mui/material';
import React from 'react';
import CodeEditor from '../components/CodeEditor';
import { Output } from '../mocks/output';
import { sendRequest } from '../service';

interface Props {
  setOutput: (output: Output[]) => void;
  text: string;
  setText: (text: string) => void;
}

function InputView(props: Props) {
  const { text, setText, setOutput } = props;

  const getOutput = async (text: string) => {
    try {
      const output = await sendRequest(text);
      setOutput(output);
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <div style={{ width: '100vw', height: '100vh', display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
      <p style={{ textAlign: 'center', fontSize: '20px' }}>Analysis tool</p>
      <CodeEditor
        text={text}
        setText={setText}
        readOnly={false}
        showPrintMargin={false}
        height="80%"
        width="100%"
        style={{ borderTop: '1px solid lightgrey' }}
        markers={[]}
      />
      <Button
        style={{ height: '11%', borderRadius: 0 }}
        onClick={() => getOutput(text)}
        variant="contained"
        disableElevation
      >
        Send code
      </Button>
    </div>
  )
}

export default InputView;
