import React from "react";
import { useNavigate } from "react-router-dom";
import { BsArrowLeft, BsPencilFill } from "react-icons/bs";

const Profile = () => {
  const navigate = useNavigate();

  const handleNavigate = () => {
    navigate(-1);
  };

  return (
    <div className="w-full h-full">
      <div className="flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5">
        <BsArrowLeft
          className="cursor-pointer text-2xl font-bold"
          onClick={handleNavigate}
        />
        <p className="cursor-pointer font-semibold">Profile</p>
      </div>

      {/* update profile pic section */}
      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput">
          <img
            className="rounded-full w-[15vw] h-[vw] cursor-pointer"
            src="https://images.pexels.com/photos/7752846/pexels-photo-7752846.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            alt=""
          />
        </label>

        <input type="file" id="imgInput" className="hidden" />
      </div>

      {/* name section */}
      <div className="bg-white px-3">
        <p className="py-3">Your name</p>
        <div className="w-full flex justify-between items-center">
          <p className="py-3">username</p>
          <BsPencilFill className="cursor-pointer" />
        </div>
      </div>
      <div className="px-3 my-5">
      <p className="py-10" >
        This is not your username or pin. This name will be visible to your
        WhatsApp contacts.
      </p>
      </div>

    </div>
  );
};

export default Profile;
