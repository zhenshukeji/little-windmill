// //验证
// export default {
//   empty: (text) => { //为空验证
//     if (text === "" || text === null || text === undefined) {
//       return true;
//     } else {
//       return false;
//     }
//   },
//   tel: (text) => { //手机号验证
//     if (!/^[1][3,4,5,6,7,8][0-9]{9}$/.test(text)) {
//       return true;
//     } else {
//       return false;
//     }
//   },
//   the: (text) => { //身份证验证
//     if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(text)) {
//       return true;
//     } else {
//       return false;
//     }
//   },
//   emoji: (text) => { //表情验证
//     if (/\ud83c[\udf00-\udfff]|\ud83d[\udc00-\ude4f]|\ud83d[\ude80-\udeff]/g.test(text)) {
//       return true; 
//     } else {
//       return false;
//     }
//   },
// };



//验证
 const empty = (text) => { //为空验证
    if (text === "" || text === null || text === undefined) {
      return true;
    } else {
      return false;
    }
  }
  
  export {
	  empty
  }



