import axios from "axios";
import React, { useEffect, useState } from "react";
import Chart from "react-apexcharts";
import sentinone from "../../static/img/sentinone.png";
import senti0 from "../../static/img/senti01.png";
import senti1 from "../../static/img/senti11.png";
import "./Senti.css";

const Senti = (props) => {
  const postIdIndex = props.user.postIdList;
  const [sentiN, setSentiN] = useState(0);
  const [sentiP, setSentiP] = useState(0);
  const [error, setError] = useState(null);

  const options = {
    chart: {
      type: "donut",
    },
    colors: ['#FFEA7D', '#CECECE'],
    plotOptions: {
      pie: {
        expandOnClick: false,
        startAngle: -90,
        endAngle: 90,
        offsetY: 10,
      },
    },
    legend: {
      show: false,
      position: "bottom",
    },
    grid: {
      padding: {
        bottom: -80,
      },
    },
    total: {
      showAlways: false,
    },
    fill: {
      colors: ['#FFEA7D', '#CECECE'],
    },
    labels: ["긍정이", "부정이"],
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200,
          },
        },
      },
    ],
  };

  const series = [sentiP, sentiN];

  // chart 값 출력
  const fetchSenti = async () => {
    const sentiN = [];
    const sentiP = [];
    try {
      for (let index = 1; index <= postIdIndex.length; index++) {
        const response = await axios.get(`http://3.37.184.148:8080/post/${postIdIndex}`);
        if (response.data.senti == 0) {
          console.log("senti1"+response.data.senti);
          sentiN.push(response.data.senti);
          console.log("sentiN"+sentiN);
          setSentiN(sentiN.length);
        } else {
          console.log("senti2"+response.data.senti);
          sentiP.push(response.data.senti);
          console.log("sentiP"+sentiP);
          setSentiP(sentiP.length);
        }
      }
    } catch (e) {
      console.log("error : " + error);
      setError(e);
    }
  };

  useEffect(() => {
    fetchSenti();
  }, []);

  return (
    <>
      <div className="Senti">
        <div className="Sentilist">
          <img
            className="SentiItem"
            src={postIdIndex.length ? senti1 : sentinone}
            onError={sentinone}
            alt=""
          />
          <img
            className="SentiItem"
            src={postIdIndex.length ? senti0 : sentinone}
            onError={sentinone}
            alt=""
          />
        </div>
        <Chart options={options} series={series} type="donut" width="550" />
      </div>
    </>
  );
};

export default Senti;