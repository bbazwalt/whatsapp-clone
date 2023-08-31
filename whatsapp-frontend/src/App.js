import {Route, Routes } from "react-router-dom";
import HomePage from "./components/homepage/HomePage";
import Status from "./components/status/Status";

const App = () => {
  return (
    <div >
      <Routes>
        <Route path="/" element={<HomePage/>}></Route>
        <Route path="/status" element={<Status/>}></Route>
        </Routes>
    </div>
  );
}

export default App;
