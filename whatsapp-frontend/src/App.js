import {Route, Routes } from "react-router-dom";
import HomePage from "./components/homepage/HomePage";
import Status from "./components/status/Status";
import StatusViewer from "./components/status/StatusViewer";
import Signin from "./components/register/Signin";

const App = () => {
  return (
    <div >
      <Routes>
        <Route path="/" element={<HomePage/>}></Route>
        <Route path="/status" element={<Status/>}></Route>
        <Route path="/status/:userId" element={<StatusViewer/>}></Route>
        <Route path="/signin" element={<Signin/>}></Route>
        </Routes>
    </div>
  );
}

export default App;
