select foodCode
	from member inner join atefoodrecord 
    on member.memberCode = atefoodrecord.memberCode 
    where member.memberCode = ? and DATE_FORMAT( atefoodrecord.ateTime ,'%Y-%m-%d' ) = DATE_FORMAT( now() ,'%Y-%m-%d' ) ;