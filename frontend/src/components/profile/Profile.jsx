import React, { useState } from "react";
import { BsArrowLeft, BsCheck2, BsPencilFill } from "react-icons/bs";
import { useDispatch, useSelector } from "react-redux";
import { updateUser } from "../../redux/auth/action";
import { Avatar } from "@mui/material";
import blankProfilePicture from "../../assets/blank-profile-picture.webp";

const Profile = ({ handleCloseOpenProfile }) => {
  const [flag, setFlag] = useState(false);
  const [username, setUsername] = useState(null);
  const [tempPicture, setTempPicture] = useState(null);
  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();

  const handleFlag = () => {
    setFlag(true);
  };

  const handleCheckClick = () => {
    setFlag(false);
    const data = {
      id: auth.reqUser?.id,
      token: localStorage.getItem("token"),
      data: { fullName: username },
    };
    dispatch(updateUser(data));
  };

  const handleChange = (e) => {
    setUsername(e.target.value);
  };

  const uploadToCloudinary = (pics) => {
    const data = new FormData();
    data.append("file", pics);
    data.append("upload_preset", "whatsapp");
    data.append("cloud_name", "dx1plneez");
    fetch("https://api.cloudinary.com/v1_1/dx1plneez/image/upload", {
      method: "post",
      body: data,
    })
      .then((res) => res.json())
      .then((data) => {
        setTempPicture(data.url.toString());
        const dataa = {
          id: auth.reqUser.id,
          token: localStorage.getItem("token"),
          data: { profilePicture: data.url.toString() },
        };
        dispatch(updateUser(dataa));
      });
  };

  return (
    <div className="w-full h-full">
      <div className="flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5">
        <BsArrowLeft
          className="cursor-pointer text-2xl font-bold"
          onClick={handleCloseOpenProfile}
        />
        <p className="cursor-pointer font-semibold">Profile</p>
      </div>

      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput">
          <Avatar
            sx={{ width: "15rem", height: "15rem" }}
            alt="profile icon"
            src={
              auth.reqUser?.profilePicture || tempPicture || blankProfilePicture
            }
          />
        </label>

        <input
          onChange={(e) => uploadToCloudinary(e.target.files[0])}
          type="file"
          id="imgInput"
          className="hidden"
        />
      </div>

      <div className="bg-white px-3">
        <p className="py-3">Your name</p>

        {!flag && (
          <div className="w-full flex justify-between items-center">
            <p className="py-3">{auth.reqUser?.fullName || "username"}</p>
            <BsPencilFill onClick={handleFlag} className="cursor-pointer" />
          </div>
        )}

        {flag && (
          <div className="w-full flex justify-between items-center py-2">
            <input
              onChange={handleChange}
              className="w-[80%] outline-none border-b-2 border-blue-700 p-2"
              type="text"
              placeholder="Enter your name"
            />
            <BsCheck2
              onClick={handleCheckClick}
              className="cursor-pointer text-2xl"
            />
          </div>
        )}
      </div>
      <div className="px-3">
        <p className="py-10">
          This is not your username or pin. This name will be visible to your
          WhatsApp contacts.
        </p>
      </div>
    </div>
  );
};

export default Profile;
