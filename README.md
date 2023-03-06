# Comgram Project
<div align="center">
<img alt="image" src="https://user-images.githubusercontent.com/82385650/223049499-b51be221-5775-48ca-81a7-24b1eefed3ee.png">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FKangWCB%2Fcomgram&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)


 **인스타그램 클론 코딩**

 **개발기간: 2022.12 ~ 2023.02**

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/shinb-bong">
        <img src="https://avatars.githubusercontent.com/u/81786969?v=4" width="110px;" alt=""/><br />
        <sub><b>신봉규</b></sub></a><br />
        <sub><b>🌱Back-end</b></sub></a><br />
    <td align="center">
      <a href="https://github.com/jw-jin">
        <img src="https://avatars.githubusercontent.com/u/82385650?v=4" width="110px;" alt=""/><br />
        <sub><b>정우진</b></sub></a><br />
        <sub><b>⚛Front-end</b></sub></a><br />    
    </td>
    
  </tr>
</table>  
</div>

# 목차
- [프로젝트 소개](#프로젝트-소개)
- [주소](#주소)
- [Stacks](#stacks)
- [시연](#시연)
  
# 프로젝트 소개
> **Comgram 프로젝트**는 인스타그램 오마주를 목표로한 프로젝트입니다.<br/>
> **ReactJS, Spring Boot, PostgreSQL** 등을 기반으로 인스타그램의 기능을 하나하나 구현하며, 다양한 기술스택을 경험하고자 했습니다.

# 주소
> **<a href="http://web.comgram.kro.kr">배포 사이트 바로가기</a>**

# Stacks

<table>
	<tr><th rowspan="5">⚛Front-end</th><td>Language</td><td>JavaScript</td></tr>
	<tr><td>Library</td><td>React</td></tr>
	<tr><td>State Container</td><td>Redux, redux-persist, redux-thunk</td></tr>
	<tr><td>View</td><td>react-modal, react-router</td></tr>
	<tr><td>Asynchronous</td><td>Axios</td></tr>
	<tr><th rowspan="5">🌱Back-end</th><td>Language</td><td>Java 11</td></tr>
	<tr><td>Framework</td><td>Spring Boot 2.7.7</td></tr>
	<tr><td>ORM</td><td>Spring Data JPA, querydsl</td></tr>
	<tr><td>Authorization</td><td>Spring Security, JWT, Oauth 2.0</td></tr>
	<tr><td>Database</td><td>PostgreSQL</td></tr>
	<tr><th rowspan="3">👨‍👩‍👦‍👦Collaboration</th><td>Api Test</td><td>Postman</td></tr>
	<tr><td>Communication</td><td>Notion, Discord</td></tr>
	<tr><td>Version Control</td><td>Github</td></tr>
	<tr><th>🛠AWS Public Cloud Service</th><td colspan="2">EC2, Route 53</td></tr>
</table>

# Comgram API

## Backend Dto List

**MemberFormDto**
```jsx
{
email,
password,
name,
nickname
}
```

**MemberLoginDto**
```jsx
{
email, // not null
password // not null, 4글자 이상 10글자 이하 비밀번호
}
```

**MemberInfoDto**
```jsx
{
memberId,
email,
nickname,
profilePhotoUrl,
name,
introMsg
}
```

**MemberUpdateForm**
```jsx
{
nickname, // not Empty
introMsg
}
```

**BoardMyListDto**
```jsx
{
id, photourl
}
```

**BoardMainDto**
```jsx
private Long id; // 게시물 id
private String  content; // 본문
private String  contentImgPath; //본문 사진
private Long likeCount = 0L; // 좋아요 수

// 작성자
private String nickName; // 작성자 별명
private String profileImgPath; // 작성자 사진
private Long writerId; // 작성자 아이디
private Boolean pushLike; // 좋아요 누른지 안누른지

private LocalDateTime regTime; // 글 작성 시간
private Long commentCount= 0L; // 댓글 갯수

private BoardLikeInfo boardLikeInfo;
private BoardCommentInfo boardCommentInfo;
```

**BoardDetailDto**
```jsx
private Long id; // 게시물 id
private String  content; // 본문
private String  contentImgPath; //본문 사진
private Long likeCount = 0L; // 좋아요 수

// 작성자
private String nickName; // 작성자 별명
private Long writerId; // 작성자 id
private String profileImgPath; // 작성자 사진
private Boolean pushLike; // 좋아요 누른지 안누른지

private LocalDateTime regTime; // 글 작성 시간
private Long commentCount= 0L; // 댓글 갯수

private List<BoardLikeInfo> boardLikeInfo;
private List<BoardCommentInfo> boardCommentInfo;
```

**SearchResponseDto**
```jsx
private String cond;
private int count;
private List<T> list;
//#일경우 cond = Board
//없을 경우 cond = Member
```

**CommentRequestDto**
```jsx
private Long id;
private String comment;
private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
private Member member;
private Board boards;
```


# 시연
<details>
  <summary><strong>로그인</strong></summary>
  
![login](https://user-images.githubusercontent.com/82385650/223057706-611fd9fc-3d3a-4800-bc8b-4ab1ec37d646.gif)

</details>
<details>
  <summary><strong>스크롤 및 프로필 조회</strong></summary>
  
![스크롤 및 프로필 클릭](https://user-images.githubusercontent.com/82385650/223058730-5eecb31d-e74a-44d3-b82c-cdafd0e852a8.gif)

</details>
<details>
  <summary><strong>유저, 글 검색</strong></summary>
  
![글검색](https://user-images.githubusercontent.com/82385650/223058916-f633392e-93da-4606-8dac-a04f007a3abf.gif)

![프로필 검색및 팔로우](https://user-images.githubusercontent.com/82385650/223058953-0b2b34d8-9862-476f-9ba6-95e585f05fa5.gif)

</details>
<details>
  <summary><strong>글쓰기, 좋아요 및 댓글</strong></summary>
  
![글쓰기](https://user-images.githubusercontent.com/82385650/223059290-a572f73a-b66d-4f9c-b586-21b53ef78eb6.gif)

![좋아요 및 댓글](https://user-images.githubusercontent.com/82385650/223059276-0a3131bc-ed92-4fcc-b176-7bc9527b2047.gif)

</details>

</details>
<details>
  <summary><strong>프로필 수정</strong></summary>
  
![프로필 수정](https://user-images.githubusercontent.com/82385650/223059712-10a0d373-febe-4d84-b9ff-0d2abc580f52.gif)

</details>





