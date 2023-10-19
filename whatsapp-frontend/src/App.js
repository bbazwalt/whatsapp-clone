import { Route, Routes } from "react-router-dom";
import HomePage from "./components/homepage/HomePage";
import Status from "./components/status/Status";
import StatusViewer from "./components/status/StatusViewer";
import Signin from "./components/register/Signin";
import Signup from "./components/register/Signup";
import Profile from "./components/profile/Profile";

const App = () => {
  return (
    <div >
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/status" element={<Status />}></Route>
        <Route path="/status/:userId" element={<StatusViewer />}></Route>
        <Route path="/signin" element={<Signin />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
        {/* <Route path="/profile" element={<Profile/>}></Route> */}

      </Routes>
    </div>
  );
}

export default App;
