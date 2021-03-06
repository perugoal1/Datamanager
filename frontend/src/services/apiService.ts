import http from './httpService';

const upload = (file: string | Blob,guidValue: string, onUploadProgress: any) => {
    let formData = new FormData();
    formData.append("file", file);
    formData.append("guid", guidValue);
    return http.post("/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        "Access-Control-Allow-Origin": "*"
      },
      onUploadProgress,
    });
};

const getData = (query: string) => {
    return http.get("/getData" + query);
};


const apiService = {
    upload,
    getData
};

export default apiService;