import { Button } from '@mui/material';
import React from 'react';
import CodeEditor from '../components/CodeEditor';
import { Output } from '../mocks/output';
import { sendRequest } from '../service';
import BugReportIcon from '@mui/icons-material/BugReport';

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

  const readFile = (file: File) => {
    const reader = new FileReader();

    reader.onload = () => {
      if (reader.result) {
        setText(reader.result as string);
      }
    }

    reader.onerror = () => {
      console.log(reader.error);
    }

    reader.readAsText(file);
  }

  return (
    <div style={{ width: '100vw', height: '100vh', display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
      <p style={{ display: 'flex', justifyContent: 'center',alignItems: 'center', fontSize: '20px' }}>
        <BugReportIcon />Bug-jet Printer
      </p>
      <CodeEditor
        text={text}
        setText={setText}
        readOnly={false}
        showPrintMargin={false}
        height="80%"
        width="100%"
        style={{ borderTop: '1px solid lightgrey', borderBottom: '1px solid lightgrey' }}
        markers={[]}
      />
      <div style={{ display: 'flex', flexDirection: 'row', margin: 'auto', gap: '100px' }}>
        <Button
          size="large"
          variant="contained"
          component="label"
        >
          Upload file
          <input type="file" accept=".java" hidden onChange={(e) => {
            if (e.target.files) {
              readFile(e.target.files[0]);
            }
          }} />
        </Button>
        <Button
          size="large"
          onClick={() => getOutput(text)}
          variant="contained"
          color="success"
        >
          Send code
        </Button>
      </div>
    </div>
  )
}

export default InputView;
