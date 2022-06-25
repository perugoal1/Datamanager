import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import UploadFiles from './components/UploadFiles';
import DataViewer from './components/DataViewer';

function App() {
  return (
    <div className="App">
      <UploadFiles />
      <DataViewer />
    </div>
  );
}

export default App;
