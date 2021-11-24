import React from 'react';
import './App.css';
import OutputView from './views/OutputView';
import InputView from './views/InputView';
import { Output } from './mocks/output';

function App() {
  const [text, setText] = React.useState("");
  const [output, setOutput] = React.useState<Output[]>([]);

  if (!output.length) {
    return <InputView text={text} setText={setText} setOutput={setOutput} />
  } else {
    return <OutputView program={text} output={output} />
  }
}

export default App;
