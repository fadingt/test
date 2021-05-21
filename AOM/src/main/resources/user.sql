SELECT x.userid, x.username, x.usercode, x.`password`, x.orgcode, x.orgname 
FROM ( SELECT 
	a.id userid, SUBSTRING_INDEX(a.REAL_NAME,'-',1) username, a.ACCOUNT_ID usercode,
	a.MD5_PWD `password`,
	b.ORG_TREE orgcode, b.ORG_NAME orgname from plf_aos_auth_user a,
(
		SELECT 
			case LENGTH(a.S_ORGCODE)
			when 10 then CONCAT(c.S_NAME,'-',b.S_NAME,'-',a.S_NAME)
			when 13 then CONCAT(d.S_NAME,'-',c.S_NAME,'-',b.S_NAME,'-',a.S_NAME)
			when 16 then CONCAT(e.S_NAME,'-',d.S_NAME,'-',c.S_NAME,'-',b.S_NAME,'-',a.S_NAME)
			end as ORG_NAME,
			case LENGTH(a.S_ORGCODE)
			when 10 then CONCAT('|',c.S_ORGCODE,'|',b.S_ORGCODE,'|',a.S_ORGCODE,'|')
			when 13 then CONCAT('|',d.S_ORGCODE,'|',c.S_ORGCODE,'|',b.S_ORGCODE,'|',a.S_ORGCODE,'|')
			when 16 then CONCAT('|',e.S_ORGCODE,'|',d.S_ORGCODE,'|',c.S_ORGCODE,'|',b.S_ORGCODE,'|',a.S_ORGCODE,'|')
			end as ORG_TREE, a.S_ORGCODE FROM paas_aom.`mdl_aos_hrorg` a
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) b on a.S_PORGCODE = b.S_ORGCODE
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) c on b.S_PORGCODE = c.S_ORGCODE
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) d on c.S_PORGCODE = d.S_ORGCODE
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) e on d.S_PORGCODE = e.S_ORGCODE
) b,
(SELECT i_userid, S_NAME from mdl_aos_empstaff where S_YPSTATE <> 11)c 
where a.ORG_CODE = b.S_ORGCODE  and a.ID = c.i_userid and a.MD5_PWD is not null 
union all 
SELECT 
	a.id userid, SUBSTRING_INDEX(a.REAL_NAME,'-',1) as '姓名', a.ACCOUNT_ID AS '工号',
	case when d.`password` is null then '21218cca77804d2ba1922c33e0151105' else d.`password` end '密码',
	b.ORG_TREE '部门id', b.ORG_NAME '部门名称' from  plf_aos_auth_user a 
join (
		SELECT
			case LENGTH(a.S_ORGCODE)
			when 10 then CONCAT(c.S_NAME,'-',b.S_NAME,'-',a.S_NAME)
			when 13 then CONCAT(d.S_NAME,'-',c.S_NAME,'-',b.S_NAME,'-',a.S_NAME)
			when 16 then CONCAT(e.S_NAME,'-',d.S_NAME,'-',c.S_NAME,'-',b.S_NAME,'-',a.S_NAME)
			end as ORG_NAME,
			case LENGTH(a.S_ORGCODE)
			when 10 then CONCAT('|',c.S_ORGCODE,'|',b.S_ORGCODE,'|',a.S_ORGCODE,'|')
			when 13 then CONCAT('|',d.S_ORGCODE,'|',c.S_ORGCODE,'|',b.S_ORGCODE,'|',a.S_ORGCODE,'|')
			when 16 then CONCAT('|',e.S_ORGCODE,'|',d.S_ORGCODE,'|',c.S_ORGCODE,'|',b.S_ORGCODE,'|',a.S_ORGCODE,'|')
			end as ORG_TREE, a.S_ORGCODE FROM paas_aom.`mdl_aos_hrorg` a
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) b on a.S_PORGCODE = b.S_ORGCODE
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) c on b.S_PORGCODE = c.S_ORGCODE
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) d on c.S_PORGCODE = d.S_ORGCODE
		left join (SELECT S_NAME, S_ORGCODE, S_PORGCODE, LENGTH(S_ORGCODE) l from paas_aom.mdl_aos_hrorg) e on d.S_PORGCODE = e.S_ORGCODE
) b on a.ORG_CODE = b.S_ORGCODE 
join (
SELECT i_userid, S_NAME from mdl_aos_empstaff where S_YPSTATE <> 11)c on a.ID = c.i_userid 
left join ngoss.t_sys_mnguserinfo d on a.id = d.userid where a.MD5_PWD is null 
union all SELECT -1,'linshi', 'linshi', '4a90d751ccdaf922c5a5ff7c182861d2', '|0001|0001001|0001001003|0001001003003|', '赞同-赞同科技-信息技术部-运维管理部' from dual 
union all SELECT -2,'test1', 'test1', 'd1959b3764003d7ed146d2910614ebe4', '|0001|0001001|0001001003|0001001003003|', '赞同-赞同科技-信息技术部-运维管理部' from dual 
)X