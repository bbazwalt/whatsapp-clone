import React, { useState } from "react";
import { BsArrowLeft, BsCheck2 } from "react-icons/bs";
import { Button, CircularProgress } from "@mui/material";

const NewGroup = () => {
  const [isImageUploading, setIsImageUploading] = useState(false);
  const [groupName, setGroupName] = useState("");
  return (
    <div className="w-full h-full">
      <div className="flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5">
        <BsArrowLeft className="cursor-pointer text-2xl font-bold" />
        <p className="text-xl font-semibold">New Group</p>
      </div>

      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput" className="relative">
          <img
            className="rounded-full w-[15vw] h-[vw] cursor-pointer"
            src="https://media.istockphoto.com/id/1168127003/vector/default-avatar-vector-placeholder-set-man-woman-child-teen-boy-girl-user-image-head.jpg?s=612x612&w=0&k=20&c=UulvDL4kySaaqFAkqLJjL4ggwbUvYKXbz5u1g1JZmbo="
            alt=""
          />
          {isImageUploading && (
            <CircularProgress className="absolute top-[5rem] left-[6rem]" />
          )}
        </label>
        <input
          type="file"
          id="imgInput"
          className="hidden"
          onChange={() => console.log("imageonchange")}
          value={""}
        />
      </div>

      <div className="w-full flex justify-between items-center py-2 px-5">
        <input
          className="w-full outline-none border-b-2 border-green-700 px-2 bg-transparent"
          placeholder="Group Subject"
          value={groupName}
          type="text"
          onChange={(e) => setGroupName(e.target.value)}
        />
      </div>
      {groupName && (
        <div className="py-10 bg-slate-200 flex items-center justify-center">
          <Button>
            {" "}
            <div className="bg-[#0c977d] rounded-full p-4">
              <BsCheck2 className="text-white font-bold text-3xl" />
            </div>{" "}
          </Button>
        </div>
      )}
    </div>
  );
};

export default NewGroup;
