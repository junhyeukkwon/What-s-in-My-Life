import React, { useEffect, useState } from "react";
import { HiOutlineHeart } from "react-icons/hi";
import "./Gallery.css";
import axios from "axios";

const Gallery = (props) => {

  const [postList, setPostListt] = useState();
  const [error, setError] = useState(null);
  
  const fetchPost = async () => {
    try {
      const token = localStorage.getItem("token");

      const postResponse = await axios.get(
        `http://localhost:8080/post/user/${token}`
      );

      setPostListt(postResponse.data);

    } catch (e) {
      console.log("error : " + error);
      setError(e);
    }
  };

  useEffect(() => {
    fetchPost();
  }, []);

  if (error) return <div>전체 게시물 에러가 발생했습니다</div>;
  if (!postList) return null;

  console.log("postList -->");
  console.log(postList);

  return (
    <div>
      {/* 메인 페이지와 똑같이 for문이나 map으로 모두 뿌려주면 될거 같다. */}
      {/* 특정 유저가 작성한 content 출력 */}
      <h1 className="profile-user-name">{postList[0].content}</h1>
      {/* 특정 유저가 업로드한 post 출력 */}
      <img src={"https://wil-s3.s3.ap-northeast-2.amazonaws.com/" + postList[0].imgList[0].file_name} alt="" />
    </div>

  );
};
export default Gallery;