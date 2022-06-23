import React, { useEffect, useState } from "react";

import apiService from '../services/apiService';

function UploadFiles() {

    const [selectedFiles, setSelectedFiles] = useState(undefined);
    const [currentFile, setCurrentFile] = useState("");
    const [progress, setProgress] = useState(0);
    const [uploadProgress, setUploadProgress] = useState(0);
    
    const [message, setMessage] = useState("");
    const [fileInfos, setFileInfos] = useState([]);

    const selectFile = (e: any) => {
        setCurrentFile(e.target.files[0]);
    };

    const upload = () => {
        const data = new FormData();
        const eventSource = new EventSource("http://localhost:8000/progress");
        let guidValue = '';
        eventSource.addEventListener("GUI_ID", (event) => {
            guidValue = JSON.parse(event.data);
            console.log(`Guid from server: ${guidValue}`);
            data.append("guid", guidValue);
            eventSource.addEventListener(guidValue, (event) => {
              const result = JSON.parse(event.data);
              if (result) {
                console.log('Progress:' + result);
                const val = parseFloat((result).toFixed(2));
                setProgress(val);
              }
              if (result === "100") {
                setProgress(100)
                eventSource.close();
              }
            });
            setProgress(0);
            setUploadProgress(0);
                apiService.upload(currentFile, guidValue, (event: any)=> {
                   console.log('success.');
                   setUploadProgress(Math.round((100 * event.loaded) / event.total));
                })
                .then((response) => {
                    setMessage(response.data.message);
                    return 'Success';
                })
                .then((files) => {
                console.log(files);
                })
                .catch(() => {
                console.error('Failed')
                });
          });


        

    };

    return (
        <div className="container my-4" style={{ textAlign: "center" }}>
            <h1>Upload CSV </h1>

            { (uploadProgress || progress) ?
                <h4 className="my-2">{uploadProgress === 100  ? (`Processing : ${progress}% `) : (`Uploading : ${uploadProgress}% `) } </h4> :''
            }
            <div className="progress  my-2">
                <div
                    className="progress-bar progress-bar-info progress-bar-striped"
                    role="progressbar"
                    style={{ width: uploadProgress + "%" }}
                >
                {uploadProgress}% Uploaded
                </div>
            </div>
            <div className="progress  my-2">
                <div
                    className="progress-bar bg-success progress-bar-striped"
                    role="progressbar"
                    style={{ width: progress + "%" }}
                >
                {progress}%
                </div>
            </div>
            <label className="btn btn-default">
                <input type="file" onChange={selectFile} />
            </label>
            <button className="btn btn-success"

            onClick={upload}
            >
                Upload
            </button>
        </div>
    );
}

export default UploadFiles;