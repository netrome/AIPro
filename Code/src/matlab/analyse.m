%% Extract data and take means

clear
%Get files, excluding . and ..
path = '../../results/';
files = dir(path);
files = files(3:end);

vals = containers.Map;
times = containers.Map;
metas = containers.Map;


%Find the longest file so we can make all the files the same length
longest = 0;
for file=files'
    longest = max(longest,length(csvread(strcat(path,file.name))));
end

%Extract all the data files and put them in hash tables
for file=files';
   data = csvread(strcat(path,file.name));
   %Extract the interesting parts of the filename
   expression = 'A-(?<a>[^-_]*)_N-(?<n>\d*)_S-(?<w>\d*)x(?<h>\d*)_E-(?<e>\d*\.\d*)_SEED-\d*.csv';
   meta = regexp(file.name,expression,'names');
   name = strcat(meta.a,meta.n,meta.w,meta.h,meta.e);
   
   v = [data(:,3);ones(longest-length(data(:,3)),1)];
   t= [data(:,2);data(end,2)*ones(longest-length(data(:,2)),1)];
   
   %If we hav seen one of these files earlier
   if(metas.isKey(name))
       vals(name) = [vals(name) v];
       times(name) = [times(name) t];
   else
       vals(name) = v;
       times(name) = t;
       metas(name) = meta;
   end
end

%calculates the means
for k = metas.keys
    times(k{1}) = mean(times(k{1}),2);
    vals(k{1}) = mean(vals(k{1}),2);
end
%% Start plotting stuff
    
figs = containers.Map;

%Plot value vs iterations
for key = metas.keys
    k = key{1}; %god damened cells... 
    figName = strcat('Covered vs iterations for ',metas(k).n,' agents on ',metas(k).w,'x',metas(k).h,' map. Easy=',metas(k).e);
    dataName = metas(k).a;
    if(figs.isKey(figName))
        figure(figs(figName));
        plot(vals(k),'displayname',dataName);
        legend('location','southeast')
        axis([0,1e4,0,1])
    else
        fig = figure('name',figName);
        hold on
        title(figName);
        plot(vals(k),'displayname',dataName)
        xlabel('Iterations')
        ylabel('Fraction of maze discovered')
        figs(figName) = fig;
    end
end

%Plot value vs time
for key = metas.keys
    k = key{1}; %god damened cells... 
    figName = strcat('Covered vs time for ',metas(k).n,' agents on ',metas(k).w,'x',metas(k).h,' map. Easy=',metas(k).e);
    dataName = metas(k).a;
    if(figs.isKey(figName))
        figure(figs(figName));
        plot(times(k),vals(k),'displayname',dataName);
        legend('location','southeast')
        axis([0,1e10,0,1])
    else
        fig = figure('name',figName);
        hold on
        title(figName);
        plot(times(k),vals(k),'displayname',dataName)
        xlabel('Time [ns]')
        ylabel('Fraction of maze discovered')
        figs(figName) = fig;
    end
end

for f = figs.values
    figur = f{1};
    saveas(figur,strcat('figs/',figur.Name,'.eps'),'epsc');
end

%save the plots

% %plot dt vs stepps
% for key = metas.keys
%     k = key{1}; %god damened cells... 
%     figName = strcat('dt vs iteraations for ',metas(k).n,' agents on ',metas(k).w,'x',metas(k).h,' map. Easy=',metas(k).e);
%     dataName = metas(k).a;
%     if(figs.isKey(figName))
%         figure(figs(figName));
%         plot(diff(times(k)),'displayname',dataName);
%         legend('location','southeast')
%     else
%         fig = figure('name',figName);
%         hold on
%         title(figName);
%         plot(diff(times(k)),'displayname',dataName)
%         xlabel('Iterations')
%         ylabel('Time for iteration [ns]')
%         figs(figName) = fig;
%     end
% end